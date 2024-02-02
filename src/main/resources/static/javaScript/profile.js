const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            customer: "",
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            games: [],
            purchases: [],
            navMenu: false
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("/api/customers")
                .then(response => {
                    this.customer = response.data
                    this.games = response.data.games
                    this.firstName = response.data.name
                    this.lastName = response.data.lastName
                    this.email = response.data.email
                    this.purchases = response.data.purchases
                    this.cart = JSON.parse(localStorage.getItem("cart")) || []

                    console.log(response.data)
                })
                .catch(error => {
                    console.log(error)
                })
        }, logout() {
            axios.post("/api/logout")
                .then(response => {
                    console.log(response)
                    this.customer = null
                    location.reload();
                    localStorage.clear();
                })
                .catch(error => console.log("Error", error))
        },
    }
}).mount("#app")
