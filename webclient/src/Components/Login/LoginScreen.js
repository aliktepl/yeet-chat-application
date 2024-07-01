import {Link, useNavigate} from 'react-router-dom';
import InputField from './InputField';
import {useState} from "react";
import img from '../../Resources/logo.png'
import LoginToast from "./LoginToast";

function LoginScreen({logIn, setProps}) {
    const navigate = useNavigate();
    const [showToast, setToast] = useState(false)

    function handleToast(){
        setToast(false)
    }
    function mouseDown() {
        document.getElementById("login-btn").style.backgroundColor = "rgba(255, 255, 255, 0.5)";
    }

    function mouseUp() {
        document.getElementById("login-btn").style.backgroundColor = "rgba(255, 255, 255, 0.7)";
    }

    async function handleLogin(event) {
        event.preventDefault();
        const username = event.target.username.value;
        const password = event.target.password.value;
        const data = {
            username: username,
            password: password
        }
        const res = await fetch("http://localhost:5000/api/Tokens/", {
                'method': 'POST', // send a post request
                'headers': {
                    'Content-Type': 'application/json', // the data (username/password) is in the form of a JSON object
                },
                'body': JSON.stringify(data) // The actual data (username/password)
            }
        )
        const token = await res.text()
        setProps({username: username, token: token})
        if (res.status === 200) {
            logIn()
            navigate("/ChatScreen")
        }
        if (res.status === 404){
            setToast(true)
            navigate("/")
        }
    }

    return (
        <div className="screen-page">
            {showToast && <LoginToast toastFunc={handleToast} render={showToast}/>}
            <div className="screen-box">
                <form onSubmit={handleLogin}>
                    <div className="header"><img src={img} alt='logo'></img></div>
                    <InputField type="text" name="username" placeholder="Username" id="usr"
                                icon={<i className="bi bi-person-fill"></i>} required/>
                    <InputField type="password" name="password" value="" id="psw"
                                pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,15}$"
                                placeholder="Password" required/>
                    <div className="input-field">
                        <input type="submit" className="submit" id="login-btn" onMouseUp={mouseUp}
                               onMouseDown={mouseDown} value="Login"/>
                    </div>
                    <div className="sign-in">
                        <p>Don't have an account? <Link to="/SignupScreen">Sign In</Link></p>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default LoginScreen;
