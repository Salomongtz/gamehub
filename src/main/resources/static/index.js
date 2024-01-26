const { createApp } = Vue;

let app = createApp({
  data() {
    return {
      navMenu: false,

      logIn: false,
      signUp: false,

      games:[],
      newGames:"",
      newest:{},
      offerGames:[],

      customer:null,

      name: "",
      lastName: "",
      email: "",
      password: "",

      error: "",

    };
  },

  
    beforeCreate(){
        axios.get("/api/games",  )
        .then(response => {
            this.games = response.data
            this.gamesDate = this.games.date
            console.log(response)
            this.newGamesFunction()
            this.offerGamesFunction()     
          })
          .catch(error => {            
            console.log("Error", error)  
          })      

      },
    

  
  

  methods: {

    newGamesFunction(){
        let juegosPorFecha = this.games.toSorted( (a, b) => {
            if (a.date < b.date) return 1
            if (a.date > b.date) return -1
            return 0
        }
        )
        this.newGames = juegosPorFecha.slice(1,6)
        this.newest= juegosPorFecha.slice(0,1)
        console.log(juegosPorFecha)
        console.log(this.newest)
        return juegosPorFecha
       
          },
    offerGamesFunction(){
        let juegosPorOferta = this.games.toSorted( (a, b) => {
            if (a.discount < b.discount) return 1
            if (a.discount > b.discount) return -1
            return 0
        }
        )
        this.offerGames = juegosPorOferta.slice(0,6)
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
          this.logIn=false
          window.location.href="index.html"

        })
        .catch(error => {
          this.error = "Incorrect username or password"
          console.log("Error", error)

        })
        axios.get("/api/customers")
        .then(response => {
            this.customer = response.data            
            console.log(response)
            
          })
          .catch(error => {            
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
    }
  },
  computed: {
    logName() {
      console.log(this.password)
    }
  }




}).mount("#app");
