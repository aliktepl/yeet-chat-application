import React, {useState} from "react";
import './App.css';
import './Design/login.css';
import './Design/sign.css';
import './Design/chat.css';
import LoginScreen from './Components/Login/LoginScreen';
import SignupScreen from './Components/SignUp/SignupScreen';
import ChatScreen from './Components/Chat/ChatScreen';
import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Protected from "./Components/Login/Protected";

function App() {

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [props, setProps] = useState({
        username:'',
        token:''
    })


    const logIn = () => {
        setIsLoggedIn(true)
    };


    return (
        <>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<LoginScreen
                        isLoggedIn={isLoggedIn} logIn={logIn} props={props} setProps={setProps}/>}></Route>
                    <Route path="/SignupScreen" element={<SignupScreen/>}></Route>
                    <Route path="/ChatScreen" element={
                        <Protected isLoggedIn={isLoggedIn}>
                            <ChatScreen isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} props={props}/>
                        </Protected>
                    }>
                    </Route>
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
