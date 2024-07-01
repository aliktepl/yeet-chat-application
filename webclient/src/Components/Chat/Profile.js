import {Link} from 'react-router-dom';
import Modal from "./Modal";
import img from "../../Resources/bowser.png"
import {useState} from "react";
import ChatToast from "./ChatToast";

function Profile({logOut, userDetails, injectContact, token}) {
    const [showToast, setToast] = useState(false)
    function handleToast(){
        setToast(false)
    }
    async function addContact(input){
        const response = await fetch('http://localhost:5000/api/Chats', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'authorization': 'bearer ' + token
            },
            body: JSON.stringify({"username": input})
        })
        if(response.status === 400){
            setToast(true)
        }
        if (response.ok) {
            const contact = await response.json()
            injectContact(contact)
        }
    }

    return (
        <>
            <div className="contact">
                {showToast && <ChatToast toastFunc={handleToast} render={showToast}/>}
                <div className="list-group-item list-group-item-action" >
                    <img src={userDetails.profilePic} className="rounded-circle user_img" alt="user_img"></img>
                    <h1 className="user_name">{userDetails.displayName}</h1>
                    <Link to='/' onClick={logOut} className="logout-button"><i
                        className="bi bi-box-arrow-left"></i></Link>
                    <button className="add-button" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        <i className="bi bi-person-fill-add"></i>
                    </button>
                </div>
            </div>
            <Modal onButtonClick={addContact}></Modal>
        </>
    );
}

export default Profile