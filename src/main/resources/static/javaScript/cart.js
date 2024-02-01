const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            
            

            navMenu: false,

      logIn: false,
      signUp: false,


      games:[],
      game:{},
      newGames:"",
      newest:{},
      offerGames:[],

      quantity:"1",
      cart:[],

      customer:null,

      juego:[],
      nombre:"",

      name: "",
      lastName: "",
      email: "",
      password: "",

      error: "",

        };
    },

    created(){
        this.loadData();
    },

    methods: {


        loadData(){
            axios.get("/api/games")
            .then(response => {
                this.games = response.data
                console.log("Games", this.games)
               
               
                
                
            })
            .catch(error => {
                console.log(error)
                // if (error.response) {
                //     Swal.fire({
                //         background: "linear-gradient(to right, #191970, #00BFFF) no-repeat 0 0 / cover",
                //         color: "white",
                //         icon: 'error',
                //         title: 'Dear customer, we must inform you:',
                //         text: `${JSON.stringify(error.response.data, null, 2)}`,
                //         footer:  `Error de respuesta: ${error.response.status}`
                //     });
                // }
            });
            axios.get("/api/customers")
            .then(response => {
                this.customer = response.data            
                console.log(response)
                this.modalLogeado()
                
              })
              .catch(error => {            
                console.log("Error", error)  
              })

              this.cart = JSON.parse(localStorage.getItem("cart")) || []
              this.nombre = this.cart.map(game => game.title)
              this.juego = this.games.filter(game=> game.title == this.nombre)

              console.log(this.cart)
                console.log(this.nombre)
                console.log(this.juego)
            
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
                this.logIn=false
      
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
                this.signUp=false
              })
              .catch(error => {
      
                let msg=error.response.data
                console.log(msg);
                // console.log("Error", error)
      
              })
          },
          logout(){
      
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
      
      
       modalLogeado(){
        let timerInterval;
        Swal.fire({
          title: "Welcome back," + this.customer.name,
          background: '#151515',
          color : 'white',
          // html: "Welcome back, " ,
          timer: 1700,
          timerProgressBar: false,
          didOpen: () => {
            Swal.showLoading();
            const timer = Swal.getPopup().querySelector("b");     timerInterval = setInterval(() => {
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