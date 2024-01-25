const { createApp } = Vue;

let app = createApp({
  data() {
    return {
        navMenu: false,

        logIn: false,
        signUp: false,

        name:"sofi",
        lastName:"",
        email:"",
        password:"",

        error:"",
    
    };
  },

  methods: {
      showMenu(){
        this.navMenu = !this.navMenu      
      },


      login(){
        axios.post("/api/login?email="+this.email+"&password="+this.password)
          .then(response => {          
              console.log(response)           
           
          })
          .catch(error => {
            this.error = "Incorrect username or password"
          console.log("Error", error)       
  
          })
      },
  
      register(){
        axios.post("/api/customers?name="+this.name+"&lastName="+this.lastName+"&email="+this.email+"&password="+this.password)
          .then(response => {
            console.log(response)
            console.log(this.name)
            this.login()
          })
          .catch(error => {
           
          console.log("Error", error)       
  
          })
      },
  


      
      formLogIn(){
        this.logIn = !this.logIn 
        this.signUp = false          
      },

      formSignUp(){
          this.signUp= !this.signUp
          this.logIn=false          
      }
  }

 

  
}).mount("#app");
