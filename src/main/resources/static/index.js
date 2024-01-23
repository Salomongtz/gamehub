const { createApp } = Vue;

let app = createApp({
  data() {
    return {
        navMenu: false,
    
    };
  },

  methods: {
      showMenu(){
        if (this.navMenu== false){
            this.navMenu = true
        }

        else{
            this.navMenu = false
        }
        
      }
  }

  
}).mount("#app");
