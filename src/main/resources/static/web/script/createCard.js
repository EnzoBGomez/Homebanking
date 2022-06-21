let URLAPI = `/api/clients/current`

Vue.createApp({
    data() {
        return{
            loading: true,
            client: {},
            cardType:"",
            cardColor:"",

        }
    },
    async created(){
        await axios.get(URLAPI)
        .then(response => {
            console.log(response)

            this.client = response.data;
            this.accounts = this.client.accounts;
            this.loans = this.client.loans;
        })

        this.loading = false;
    },
    methods:{
        async logout(){
            //console.log("asd")
            await axios.post('/api/logout').then(response => {
                console.log('signed out!!!');
                location.href ='/web/index.html';
            })
        },
        addCard(){
            axios.post('/api/clients/current/cards', `cardType=${this.cardType}&cardColor=${this.cardColor}`)
            .then(response => {
                location.href ='/web/cards.html';
              })
        }
    }
}).mount('#app')