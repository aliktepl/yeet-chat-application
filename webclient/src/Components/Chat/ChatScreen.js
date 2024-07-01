import Profile from "./Profile";
import Contact from "./Contact";
import {useEffect, useState} from "react";
import CurrentChat from "./CurrentChat";
import EmptyChat from "./EmptyChat";
import io from "socket.io-client"
const socket = io("http://localhost:5000", {autoConnect: false, forceNew: true} )


function ChatScreen({setIsLoggedIn, props}) {
    const [contacts, setContacts] = useState([]);
    const [messages, setMessages] = useState([])
    const [userDetails, setUserDetails] = useState([]);
    const [contactIndex,setContactIndex] = useState('');

    const logOut = () => {
        socket.off("message")
        //socket.off("newUser")
        socket.disconnect()
        setIsLoggedIn(false)
        socket.off()
    };

    async function getMessages(index) {
        const res = await fetch("http://localhost:5000/api/Chats/" + contacts[index].id + "/Messages/", {
                'headers': {
                    'Content-Type': 'application/json',
                    'authorization': 'bearer ' + props.token // attach the token
                },
            }
        )
        const messages = await res.json()
        setMessages(messages)
    }

    async function sendMessage(message) {
        const res = await fetch("http://localhost:5000/api/Chats/" + contacts[contactIndex].id + "/Messages/", {
                'method': 'POST', // send a post request
                'headers': {
                    'Content-Type': 'application/json',
                    'authorization': 'bearer ' + props.token
                },
                'body': JSON.stringify({"msg": message.text})
            }
        )
        await res.text()
        if (res.ok) {
            socket.emit('message', contacts[contactIndex].user.username)
        }
    }

    async function getContacts() {
        const res = await fetch('http://localhost:5000/api/Chats/', {
                'headers': {
                    'Content-Type': 'application/json',
                    'authorization': 'bearer ' + props.token
                },
            }
        )
        const contacts = await res.json()
        setContacts(contacts)
    }

    async function getUserDetails() {
        try {
            const res = await fetch('http://localhost:5000/api/Users/' + props.username + '/', {
                headers: {
                    'Content-Type': 'application/json',
                    'authorization': 'bearer ' + props.token // Attach the token with 'Bearer' prefix
                }
            });

            if (res.status === 200) {
                // Token is valid, proceed with retrieving user details
                const userDetails = await res.json();
                setUserDetails(userDetails);
            } else {
                // Token is not valid, handle the error accordingly
            }
        } catch (error) {
            console.log('Error occurred:', error)
            // Handle the error
        }
    }

    useEffect( () => {
        async function Initialize() {
            await getUserDetails()
            await getContacts()

            socket.on('connect', () => {
                socket.emit("newUser", props.username)
                socket.on('message', () => {
                    getContacts()
                })
            })
            socket.connect()
        }
        Initialize().then(() => {})
    },[])

    useEffect(() => {
        if (contactIndex !== '') {
            getMessages(contactIndex).then(() => {})
        }
    }, [contacts])

    // gets index from contact and does something with it
    async function handleContactIndex(index) {
        if (contactIndex) {
            await socket.emit('leave', contacts[contactIndex].user.username)
        }
        setContactIndex(index)
        await getMessages(index)
        socket.emit('chat', contacts[index].user.username)
    }

    function handleContact(){
        getContacts().then(() => {})
    }

    return (
        <div className="chat-page">
            <div className="chat-box">
                <div className="left">
                    <div className="profile">
                        {/*user profile*/}
                        <Profile logOut={logOut} userDetails={userDetails} injectContact={handleContact}
                                 token={props.token}/>
                    </div>
                    <div className="list-group contact_list">
                        {/*contacts list*/}
                        { contacts &&
                        <Contact contacts={contacts} handleContactIndex={handleContactIndex}
                                 activeContactIndex={contactIndex} messages={messages}></Contact>}
                    </div>
                </div>
                {
                    contacts ? <div className="right">
                        <CurrentChat contact={contacts[contactIndex]} contactIndex={contactIndex} messages={messages} sendMessage={sendMessage} getMessages={getMessages} getContacts={getContacts}></CurrentChat>
                    </div> : <EmptyChat></EmptyChat>
                }

            </div>
        </div>
    );
}

export default ChatScreen