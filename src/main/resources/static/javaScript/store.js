const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            modalFilter:false,

            navMenu: false,
            games: [],
            filteredGames: [],
            selectGenre: "",
            selectPlatform: "",
            search: "",
            games: [],
            genres: [],
            platforms: [],
            price: [],
            modalOpen: false,
            selectedGame: [],
            filteredGames: [],
            selectedPriceRange: 0,

            logIn: false,
            signUp: false,

            customer: null,

      name: "",
      lastName: "",
      email: "",
      password: "",

      error: "",

        };
    },
    created() {
        this.loadData()
    },

    methods: {
        loadData() {
            axios.get("/api/games")
                .then(response => {
                    this.games = response.data
                    console.log("Games", this.games)
                    this.filteredGames = response.data
                    console.log("GamesFiltered", this.filteredGames)
                    this.genres = [...new Set(response.data.flatMap(data => data.genre))].sort();
                    console.log("Generos", this.genres)
                    this.platforms = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("plataformas", this.platforms)
                    this.price = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("Precios", this.price)
                    this.cart = JSON.parse(localStorage.getItem("cart")) || []
                    this.selectGenre = new URLSearchParams(window.location.search).get("genre").replace(/"/g, '')
                    console.log(this.selectGenre)
                    this.filterCrossSearch()
                })
                .catch(error => {
                    if (error.response) {
                        Swal.fire({
                            background: "black",
                            color: "yellow",
                            icon: 'error',
                            title: 'Dear gamer, we must inform you:',
                            text: `${JSON.stringify(error.response.data, null, 2)}`,
                            footer: `Error de respuesta: ${error.response.status}`
                        });
                    }
                });

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


        // ------------------FILTROS---------------------

        toggleFlip(game) {
            // Alternar la propiedad flipped de la tarjeta
            game.flipped = !game.flipped;
        },


        filterGenre(event) {
            this.selectGenre = event.target.value;
            console.log("Selected Genre", this.selectGenre);
            this.filterCrossSearch();
        },

        filterPlatforms(event) {
            this.selectPlatform = event.target.value;
            console.log("Selected Platform", this.selectPlatform);
            this.filterCrossSearch();
        },

        filterTitle(event) {
            this.search = event.target.value;
            console.log("Selected Title", this.search);
            this.filterCrossSearch();
        },
       
        filterPrice(event) {
            this.selectedPirce = event.target.value;
            console.log("Selected Price", this.selectedPirce);
            this.filterCrossSearch();
        },

        filterDate(event) {
            this.selectedDate = event.target.value;
            console.log("Selected Date", this.selectedDate);
            this.filterCrossSearch();
        },

        // Método para ordenar los juegos por precio de menor a mayor
        sortGamesByPriceAsc() {
            this.filteredGames.sort((a, b) => a.price - b.price);
        },

        // Método para ordenar los juegos por precio de mayor a menor
        sortGamesByPriceDesc() {
            this.filteredGames.sort((a, b) => b.price - a.price);
        },

        sortGamesByDateAsc() {
            this.filteredGames.sort((a, b) => {
                const dateA = new Date(a.date);
                const dateB = new Date(b.date);
                return dateA - dateB;
            });
        },

        sortGamesByDateDesc() {
            this.filteredGames.sort((a, b) => {
                const dateA = new Date(a.date);
                const dateB = new Date(b.date);
                return dateB - dateA;
            });
        },


        filterCrossSearch() {
            console.log("Cross Search:", this.search);
            console.log("Cross platform:", this.selectPlatform);
            console.log("Cross genre:", this.selectGenre);
            console.log("Cross price:", this.selectedPirce);

            const aux = this.games.filter(game =>
                (!this.search || (game.title && game.title.toLowerCase().includes(this.search.toLowerCase()))) &&
                (!this.selectPlatform || this.selectPlatform === 'all' || (game.platforms && game.platforms.some(platform => platform.toLowerCase().includes(this.selectPlatform.toLowerCase())))) &&
                (!this.selectGenre || this.selectGenre === 'all' || (game.genre && game.genre.some(genre => genre.toLowerCase().includes(this.selectGenre.toLowerCase())))) &&
                (!this.selectedPirce || game.price <= this.selectedPirce) &&
                (!this.selectedDate || game.date <= this.selectedDate)
            );

            this.sortGamesByPriceAsc();
            this.sortGamesByPriceDesc();
            this.sortGamesByDateAsc();
            this.sortGamesByDateDesc();

            this.filteredGames = aux;
            console.log("Filtered Games", this.filteredGames);
        },

        openModal(game) {
            this.selectedGame = game
            this.modalOpen = true
            console.log(this.selectedGame)
        },

        closeModal() {
            this.modalOpen = false
            console.log(this.modalOpen)

        },



        filterMenu(){
            this.modalFilter = !this.modalFilter
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
                this.cart = this.customer.cart
                localStorage.setItem("cart", JSON.stringify(this.cart))
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
          title: "Welcome back, " + this.customer.name,
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