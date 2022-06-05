let URLAPI = `http://localhost:8080/api/clients/current`

Vue.createApp({
    data() {
        return{
            flip: false,
            loading: true,
            client:{},
            cards:[],
            arrayNumberCard:[],
            debitCards: [],
            creditCards: []
        }
    },
    async created(){
        await axios.get(URLAPI)
        .then(response => {
            console.log(response)
            this.client = response.data;
            this.cards = this.client.cards;
            this.debitCards = this.cards.filter(card => card.type == 'DEBIT')
            this.creditCards = this.cards.filter(card => card.type == 'CREDIT')
        })
        this.loading = false;
    },
    methods:{
        separarNumerosCard(card){
            var res = [...card].reduce((p, c, i) => p += (i && !(i % 4)) ? "-" + c : c, "").split('-');

            return res;
        },
        getExpiresDate(dateImput){
            const date = new Date(dateImput)
            const dateYear = date.getFullYear()
            const getTwoDigits = dateYear.toString().substring(2)
            return (date.getMonth() + 1) + "/" + getTwoDigits 
        },
        async logout(){
            console.log("asd")
            await axios.post('/api/logout').then(response => console.log('signed out!!!'));
            location.href ='http://localhost:8080/web/index.html';
        },
        toCreateCard(){
            location.href='http://localhost:8080/web/create-cards.html';
        }
    }
}).mount('#app')