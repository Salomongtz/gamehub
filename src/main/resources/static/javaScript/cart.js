const { createApp } = Vue;

let app = createApp({
  data() {
    return {



      navMenu: false,

      logIn: false,
      signUp: false,


      games: [],
      game: {},
      newGames: "",
      newest: {},
      offerGames: [],

      platform: [],

      quantity: "1",
      cart: [],

      customer: null,

      juego: [],
      nombre: "",
      gameName: [],

      name: "",
      lastName: "",
      email: "",
      password: "",

      error: "",

      showPaymentForm: false,
      cardNumber: '',
      cardCVV: '',
      holder: '',
      selectedMonth: '',
      months: [
        'January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'
      ],
      year: '',
      selectedYear: '',
      availableYears: [],
      total: '',
      description: '',

    };
  },

  created() {
    const currentYear = new Date().getFullYear();
    for (let i = currentYear; i <= currentYear + 10; i++) {
      this.availableYears.push(i);
    }
    this.loadData();


  },

  methods: {

    showPaymentForm() {
      this.showPaymentForm = true
    },
    
    loadData() {
      axios.get("/api/games")
        .then(response => {
          this.games = response.data
          console.log("Games", this.games)
          console.log(this.customer)
          this.gameName = this.games.map(game => game.title)
          this.cart = JSON.parse(localStorage.getItem("cart")) || []
          this.nombre = this.cart.map(game => game.title)
          this.juego = this.games.filter(game => this.nombre.includes(game.title))

          for (game of this.juego) {
            for (cartGame of this.cart) {
              console.log("Cart Game: " + cartGame.title);
              if (cartGame.title == game.title) {
                console.log(cartGame.title + " == " + game.title)
                console.log(game.quantity + " ==> " + cartGame.quantity)
                game.quantity = cartGame.quantity
              }
            }
            console.log(game.quantity);
          }
          this.total = this.juego.reduce((subtotal, game) => subtotal + ((game.price * (1 - game.discount)) * game.quantity), 0)

          console.log(this.total);
          // this.platform = this.juego.map(game => game.platforms)
          console.log(this.cart)
          console.log(localStorage.getItem("cart").toString())
          console.log(this.juego)
          console.log(this.platform)
          console.log(this.nombre.map(title => `- ${title}`).join('\n'));
        })
        .catch(error => {
          console.log(error)

        });
      axios.get("/api/customers")
        .then(response => {
          this.customer = response.data
          this.cart = this.customer.cart
          console.log(response)

        })
        .catch(error => {
          console.log("Error", error)
        })

    },
    add(game) {
      if (game.stock > game.quantity) {
        console.log(this.cart);
        console.log(game.price);
        game.quantity++
        this.total += game.price * (1 - game.discount)
        console.log(this.cart);
        this.updateCart(game.title, game.quantity)
        console.log(this.cart);
      }
    },

    substract(game) {
      if (game.quantity > 1) {
        game.quantity--
        this.total -= game.price * (1 - game.discount)
        console.log(this.cart);
        this.updateCart(game.title, game.quantity)
        console.log(this.cart);
      } else if (game.quantity == 1) {
        this.removeFromCart(game)
      }
    },
    updateCart(title, quantity) {
      let cart = JSON.parse(localStorage.getItem("cart")) || []
      let aux = cart.find(game => game.title == title)

      if (aux) {
        aux.quantity = quantity
        console.log("Quantity +1!");
      }
      else {
        let item = {
          title: title,
          quantity: quantity
        }
        cart.push(item)
      }
      localStorage.setItem("cart", JSON.stringify(cart))

    },
    clearCart() {
      Swal.fire({
        background: '#151515',
        color: 'white',
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#452C6D",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            background: '#151515',
            color: 'white',
            title: "Deleted!",
            text: "Your file has been deleted.",
            icon: "success"
          }).then(() => {
            localStorage.removeItem("cart")
            axios.patch("/api/customers/cart", { cart: localStorage.getItem("cart") })
            this.loadData()
          })

        }
      })

    },
    removeFromCart(game) {
      let cart = JSON.parse(localStorage.getItem("cart")) || []
      let aux = cart.find(item => item.title == game.title)

      if (aux) {
        cart = cart.filter(item => item.title != game.title)
        localStorage.setItem("cart", JSON.stringify(cart))
        console.log(aux.title + " removed from cart")
        this.loadData();
      }
    },
    checkout() {
      Swal.fire({
        background: '#151515',
        color: 'white',
        title: "Processing...",
        html: "Your payment is being processed, please don't close this window.",
        timer: 0,
        timerProgressBar: true,
        didOpen: () => {
          Swal.showLoading()
        }
      })
      let purchaseDescription = this.nombre.map(title => `- ${title}`).join('\n')
      axios.post('https://vertex-5ys8.onrender.com/api/cards/payments', {
        number: this.cardNumber,
        cvv: this.cardCVV,
        amount: this.total,
        description: "GameHub purchase:" + purchaseDescription
      })
        .then((response) => {
          Swal.close()
          console.log(response.data);
          let cart = JSON.parse(localStorage.getItem("cart")) || []
          console.log(cart);
          axios.post('/api/purchase', cart)
            .then(response => {
              console.log(response.data);
              Swal.fire({
                background: '#151515',
                color: 'white',
                confirmButtonColor: '#452C6D',
                title: "Sweet!",
                text: "Your purchase was successful! A receipt has been sent to your email",
                imageUrl: "../assets/images/konata.png",
                imageWidth: 400,
                imageHeight: 250,
                imageAlt: "Custom image"
              })
            }).then(() => {
              this.showPaymentForm = false
            }).catch(error => {
              console.error(error)
              Swal.fire({
                icon: "error",
                title: "Oops...",
                text: error.data,
              })
            })
        })
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
          this.patchData()
          this.customer = null
          location.reload()
          localStorage.clear()
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
        title: "Welcome back," + this.customer.name,
        background: '#151515',
        color: 'white',
        // html: "Welcome back, " ,
        timer: 1700,
        timerProgressBar: false,
        didOpen: () => {
          Swal.showLoading();
          const timer = Swal.getPopup().querySelector("b"); timerInterval = setInterval(() => {
            timer.textContent = `${Swal.getTimerLeft()}`;
          }, 100);
        },
        willClose: () => {
          clearInterval(timerInterval);
        }
      }).then((result) => {
        /* Read more about handling dismissals below */
        if (result.dismiss === Swal.DismissReason.timer) {
          console.log("I was closed by the timer");
        }
      })
    },
  }

}).mount("#app");