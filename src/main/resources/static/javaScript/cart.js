const { createApp } = Vue;

let app = createApp({
  data() {
    return {
      navMenu: false,

      logIn: false,
      signUp: false,

      cart: [],

      customer: null,

      nombre: "",
      gameName: [],

      name: "",
      lastName: "",
      email: "",
      password: "",
      error: "",
    };
  },

  created() {
    this.loadData();


  },

  methods: {


    loadData() {
      // Hacer ambas solicitudes simultáneamente
      Promise.all([
        axios.get("/api/games"),
        axios.get("/api/customers")
      ])
        .then(responses => {
          // Desempaquetar las respuestas
          const [gamesResponse, customersResponse] = responses;

          // Procesar la respuesta de juegos
          this.games = gamesResponse.data;
          console.log("Games", this.games);

          this.cart = JSON.parse(localStorage.getItem("cart")) || [];
          this.cart.forEach(game => {
            game.quantity = 1;
          });
          this.nombre = this.cart.map(game => game.title);
          console.log("Carrito Vue", this.cart);
          console.log("Carrito LocalStore", localStorage.getItem("cart").toString());
          console.log("Juegos nombres Filtrados", this.nombre);
          console.log("Plataforma", this.platform);

          // Procesar la respuesta de clientes
          this.customer = customersResponse.data;

          console.log("Customer", customer);
          this.modalLogeado();
        })
        .catch(error => {
          console.log(error);
        });
    },

    calculateTotal() {
      let total = 0;
      for (let Cart of this.cart) {
        total += Cart.price * this.quantity;
      }
      return total;
    },
    getQuantity(Cart) {
      const cartItem = this.cart.find(item => item.title === Cart.title);
      return cartItem ? cartItem.quantity : 0;
  },
  
  add(Cart) {
    const cartItem = this.cart.find(item => item.title === Cart.title);
    if (cartItem) {
      cartItem.quantity++;
    } else {
      this.cart.push({ title: Cart.title, quantity: 2 }); // Ajusta como desees
    }
    // ... Actualiza el localStorage y realiza otras operaciones necesarias
  },
  
  remove(Cart) {
    const cartItem = this.cart.find(item => item.title === Cart.title);
    if (cartItem && cartItem.quantity > 1) {
      cartItem.quantity--;
    } else {
      this.cart = this.cart.filter(item => item.title !== Cart.title);
    }
    // ... Actualiza el localStorage y realiza otras operaciones necesarias
  },
    showMenu() {
      if (this.navMenu == false) {
        this.navMenu = true
      }

      else {
        this.navMenu = false
      }

    },

    login() {
      axios.post("/api/login?email=" + this.email + "&password=" + this.password)
        .then(response => {

          console.log(response)
          this.logIn = false

          axios.get("/api/customers")
            .then(response => {
              this.customer = response.data
              console.log(response)
              this.modalLogeado()
              this.cart = this.customer.cart
              localStorage.setItem("cart", JSON.stringify(this.cart))
            })
            .catch(error => {
              console.log("Error", error)
            })

        })
        .catch(error => {
          this.error = "Incorrect username or password"
          console.log("Error", error)

        })

    },

    register() {
      let user = { firstName: this.name, lastName: this.lastName, email: this.email, password: this.password }

      axios.post(`/api/customers`, user)
        .then(response => {
          console.log(response)
          console.log(this.name)
          this.login()
          this.signUp = false
        })
        .catch(error => {

          let msg = error.response.data
          console.log(msg);
          // console.log("Error", error)

        })
    },
    logout() {
      axios.post("/api/logout")
        .then(response => {
          console.log(response)
          this.customer = null
          location.reload();
          localStorage.clear();
        })
        .catch(error => console.log("Error", error))
    },



    patchData() {
      axios.patch("/api/customers/cart", { cart: localStorage.getItem("cart").toString() })
        .then(response => {

          console.log(response)
        })
        .catch(error => console.log("Error", error))
      console.log(this.customer)
    },

    formLogIn() {
      this.logIn = !this.logIn
      this.signUp = false
    },

    formSignUp() {
      this.signUp = !this.signUp
      this.logIn = false
    },

    // ------------------LOGIN ALERT----------------------


    modalLogeado() {
      let timerInterval;
      Swal.fire({
        title: "Welcome back, " + this.customer.name,
        background: '#151515',
        color: 'white',
        timer: 1700,
        timerProgressBar: false,
        didOpen: () => {
          Swal.showLoading();
          const timer = Swal.getPopup().querySelector(".swal2-timer-progress-bar");
          timerInterval = setInterval(() => {
            if (timer) {
              timer.textContent = `${Swal.getTimerLeft()}`;
            }
          }, 100);
        },
        willClose: () => {
          clearInterval(timerInterval);
        }
      }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
          console.log("I was closed by the timer");
        }
      });
    },

  }


}).mount("#app");