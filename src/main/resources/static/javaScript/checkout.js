const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            cardNumber: '',
            cardCVV: '',
            amount: '',
            description: '',
            cart: [],
        }
    },
    created() {
        this.loadData()
    }, methods: {
        loadData() {
            console.log(localStorage.getItem("cart"));
            this.cart = JSON.parse(localStorage.getItem("cart")) || []
            console.log(this.cart);
        },
        submitForm() {
            axios.post('http://vertex-5ys8.onrender.com/api/cards/payments', {
                number: this.cardNumber,
                cvv: this.cardCVV,
                amount: this.amount,
                description: this.description
            }).then(response => {
                axios.post('')
                console.log(response.data);
            }).catch(error => {
                console.error(error);
            });
        }
    },
})

app.mount("#app")