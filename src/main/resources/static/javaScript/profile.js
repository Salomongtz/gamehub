const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            name: "",
            lastName: "",
            email: "",
            password: "",
            games: [],
            newGames: [],
            offerGames: [],
            newest: [],
        };
    },
    methods: {
        loadData() {
            axios.get("/api/customers")
                .then(response => { console.log(response.data) })
                .catch(error => {
                    console.log(error)
                })
        }
    },
    created() {
        this.loadData()
    }
});
app.mount("#app");