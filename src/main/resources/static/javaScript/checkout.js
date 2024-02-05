const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            cardNumber: '',
            cardCVV: '',
            amount: '',
            description: '',
            cart: [],
            games: [],
            cartTitles: [],
            cartGames: [],
        }
    },
    created() {
        this.loadData()
    }, methods: {
        loadData() {
            console.log(localStorage.getItem("cart"))
            console.log(this.cart)
            axios.get("/api/games")
                .then(response => {
                    this.cart = JSON.parse(localStorage.getItem("cart")) || []
                    this.games = response.data
                    console.log("Games", this.games)
                    console.log(this.customer)
                    this.cartTitles = this.cart.map(game => game.title)
                    this.cartGames = this.games.filter(game => this.cartTitles.includes(game.title))
                    // this.platform = this.juego.map(game => game.platforms)
                    console.log(this.cart)
                    console.log(localStorage.getItem("cart").toString())
                    console.log(this.cartTitles)
                    console.log(this.cartGames)
                })
                .catch(error => {
                    console.log(error)

                })
        },
        submitForm() {
            axios.post('http://vertex-5ys8.onrender.com/api/cards/payments', {
                number: this.cardNumber,
                cvv: this.cardCVV,
                amount: this.amount,
                description: this.description
            }).then(response => {
                axios.post('/api/purchase', this.cart)
                console.log(response.data);
            }).catch(error => {
                console.error(error);
            });
        }
    },
})

app.mount("#app")