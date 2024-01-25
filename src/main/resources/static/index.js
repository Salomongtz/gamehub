const { createApp } = Vue;

let app = createApp({
  data() {
    return {
      navMenu: false,

      logIn: false,
      signUp: false,

      name: "",
      lastName: "",
      email: "",
      password: "",

      error: "",

    };
  },
  created() {
    let str = `asd#asd`
    console.log(str);
  },

  methods: {
    showMenu() {
      this.navMenu = !this.navMenu
    },


    login() {
      axios.post("/api/login?email=" + this.email + "&password=" + this.password)
        .then(response => {
          console.log(response)

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
        })
        .catch(error => {

          let msg=error.response.data
          console.log(msg);
          // console.log("Error", error)

        })
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
