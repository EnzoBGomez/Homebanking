
Vue.createApp({
    data() {
        return{
            email:"",
            password:"",
            firsNameRegister:"",
            lastNameRegister:"",
            emailRegister:"",
            passwordRegister:"",
            
            
        }
    },
    created(){
        // axios.post('/api/clients',"firstName=pedro2&lastName=rodriguez&email=pedro@mindhub.com&password=pedro",{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => console.log('registered')) 
    },
    methods:{
        login(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => {console.log('signed in!!!');location.href ='http://localhost:8080/web/accounts.html';})
        },
        async register(){
            if(!this.emailRegister.includes("@admin")){
                this.emailInvalid = false;
                await axios.post('/api/clients',`firstName=${this.firsNameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&password=${this.passwordRegister}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => {console.log('registered');})
            axios.post('/api/login',`email=${this.emailRegister}&password=${this.passwordRegister}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => {console.log('signed in!!!');location.href ='http://localhost:8080/web/accounts.html';})
            }
            else{
                this.emailInvalid = true;
            }
        },
        
        
    }
}).mount('#app')