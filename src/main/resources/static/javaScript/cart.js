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

      platform:[],

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

     

    };
  },

  created() {
    this.loadData();
    
    
  },

  methods: {


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
          // this.platform = this.juego.map(game => game.platforms)
          console.log(this.cart)
          console.log(localStorage.getItem("cart").toString())
          console.log(this.juego)
          console.log(this.platform)
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
    add() {
      this.quantity++
    },

    remove() {
      this.quantity--
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