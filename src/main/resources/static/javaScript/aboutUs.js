const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            navMenu: false,
            games: [],
            filteredGames: [],
            genres: [],
            platforms: [],
            price: [],
            cart: [],

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
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("/api/games")
                .then(response => {
                    this.games = response.data;
                    console.log("Games", this.games);
                    this.filteredGames = response.data;
                    console.log("GamesFiltered", this.filteredGames);
                    this.genres = [...new Set(response.data.flatMap(data => data.genre))].sort();
                    console.log("Generos", this.genres);
                    this.platforms = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("plataformas", this.platforms);
                    this.price = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("Precios", this.price);
                    this.cart = JSON.parse(localStorage.getItem("cart")) || []

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

// Aquí quitamos el código que estaba dentro del método "created"
// var header = document.getElementById('myHeader');
// var page = document.getElementById('page');
// var openMenuButton = document.getElementById('openmenu');

// window.addEventListener('scroll', function () {
//     page.classList.remove('menuopen');
//     if (window.scrollY >= 100) {
//         header.classList.add('sticky');
//     } else {
//         header.classList.remove('sticky');
//     }
// });

// Event listener to remove the sticky class when the button is clicked
// openMenuButton.addEventListener('click', function () {
//     header.classList.remove('sticky');
//     page.classList.add('menuopen');
// });

var links = document.querySelectorAll('a[href^="#"]');

links.forEach(function (link) {
    link.addEventListener('click', function (event) {
        // Prevent the default action
        event.preventDefault();

        // Get the target element
        var targetId = this.getAttribute('href');
        var targetElement = document.querySelector(targetId);

        // Smooth scroll to target
        if (targetElement) {
            targetElement.scrollIntoView({
                behavior: 'smooth'
            });
        }
    });
});
