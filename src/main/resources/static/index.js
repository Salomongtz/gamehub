const { createApp } = Vue;

let app = createApp({
  data() {
    return {
        navMenu: false,

        logIn: false,
        signUp: false,
    
    };
  },

  methods: {
      showMenu(){
        this.navMenu = !this.navMenu      
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
