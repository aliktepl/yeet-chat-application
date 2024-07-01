import {useEffect, useRef} from "react";
import Message from "./Message";
import MessageBox from "./MessageBox";

function CurrentChat({contact, contactIndex, messages, sendMessage, getMessages, getContacts}) {
    const bottomRef = useRef(null)

    useEffect(() => {
        bottomRef.current?.scrollIntoView({behavior: 'smooth'});
    }, [messages]);

    async function handleSendMessage(newMessage) {
        await sendMessage(newMessage)
        // await getMessages(contactIndex)
        await getContacts()
    }

    return (
        <>
            <div className="curr-chat">
                { contact &&
                <div className="curr_contact">
                    {/* Display info about current contact */}
                    <img src={contact.user.profilePic} className="rounded-circle prof_img" alt="prof_img"></img>
                    <h1 className="prof_name">{contact.user.displayName}</h1>
                </div>
                }
            </div>
            <div className="messages">
                <div className="texting">
                    {/*Print the messages*/}
                    <Message messages={messages} contact={contact}/>
                    <div ref={bottomRef}/>
                </div>
                <div className="send-message-block">
                    {/* Type and send message */}
                    {contact && <MessageBox onSendMessage={handleSendMessage}/>}
                </div>
            </div>
        </>
    );
}

export default CurrentChat;
