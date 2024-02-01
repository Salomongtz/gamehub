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

      quantity: 0,
      cart: [],

      customer: null,

      name: "",
      lastName: "",
      email: "",
      password: "",

      error: "",

    };
  },


  beforeCreate() {
    axios.get("/api/games",)
      .then(response => {
        this.games = response.data
        this.gamesDate = this.games.date
        const searchId = location.search
        const paramsId = new URLSearchParams(searchId)
        const ID = paramsId.get('id')

        this.game = this.games.find(game => game.id == ID)
        console.log(response)
        console.log(this.game)

        this.offerGamesFunction()
      })
      .catch(error => {
        console.log("Error", error)
      }),

      axios.get("/api/customers")
        .then(response => {
          this.customer = response.data
          this.cart = response.data.cart

          console.log(response)


        })
        .catch(error => {
          this.customer = null,
            console.log("Error", error)
        })


  },





  methods: {
    offerGamesFunction() {
      let juegosPorOferta = this.games.toSorted((a, b) => {
        if (a.discount < b.discount) return 1
        if (a.discount > b.discount) return -1
        return 0
      }
      )
      this.offerGames = juegosPorOferta.slice(0, 6)
      console.log(this.offerGames)
    },




    // ---------FUNCIONES DE MODAL PARA LOGIN Y HAMB MENU---------------------
    showMenu() {
      this.navMenu = !this.navMenu
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

        })
        .catch(error => console.log("Error", error))
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

    add() {
      this.quantity++
    },

    remove() {
      this.quantity--
    },

    addToCart(gameName, quantity) {

      let cart = JSON.parse(localStorage.getItem("cart")) || []
      let aux=cart.find(game =>game.title == gameName)
      if (aux) {
       aux.quantity += quantity

      }

      else {

        let item = {
          title: gameName,
          quantity: quantity
        }

        cart.push(item)
      }
      localStorage.setItem("cart", JSON.stringify(cart))

      //para sacar de favo


      // else {
      //   cart.push(gameName,quantity)   
      //   this.cartAdded()           

      // }
      // localStorage.setItem('cart', JSON.stringify(cart))



    },


    agregarAlCarro(articulo) {
      let storageCarrito = JSON.parse(localStorage.getItem('carrito')) || [];
      const index = storageCarrito.findIndex(item => item.id === articulo._id);
      let cart = {

      }

      if (index !== -1) {
        if (storageCarrito[index].cantidad + 1 > articulo.disponibles) {
          alert("Se excedió el límite de stock para este artículo");
          return; // No agregar más al carrito si excede el límite
        }
        storageCarrito[index].cantidad++;
      } else {
        if (1 > articulo.disponibles) {
          alert("Se excedió el límite de stock para este artículo");
          return; // No agregar al carrito si excede el límite
        }
        storageCarrito.push({ id: articulo._id, cantidad: 1 });
      }
      localStorage.setItem('carrito', JSON.stringify(storageCarrito))
      this.localStorage = storageCarrito
    }, // finaliza AgregarAlCarro






    cartAdded() {
      Swal.fire({
        title: "Succesfully added to cart!",
        background: '#151515',
        color: 'white',
        icon: "success",

        showDenyButton: true,
        confirmButtonText: "See cart",
        denyButtonText: `Maybe later`,
        denyButtonColor: '#151515',
        confirmButtonColor: "#452C6D",

      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
          Swal.fire("Saved!", "", "success");
        } else if (result.isDenied) {
          Swal.close()
        }
      });
    }

  },










}).mount("#app");
