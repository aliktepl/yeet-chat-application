import {useState} from "react";

function MessageBox({onSendMessage}) {
    const [inputValue, setInputValue] = useState('');

    function sendMessage() {
        if (inputValue.trim() === '') {
            return;
        }
        const newMessage = {
            text: inputValue,
            timestamp: new Date().toLocaleTimeString(),
        };
        onSendMessage(newMessage);
        setInputValue('');
    }

    function handleKeyDown(event) {
        if (event.key === "Enter") {
            event.preventDefault();
            sendMessage();
        }
    }

    function handleInputChange(event) {
        setInputValue(event.target.value);
    }

    return (
        <>
            <input type="text" className="input" placeholder="Type a message..." value={inputValue}
                   onChange={handleInputChange} onKeyDown={handleKeyDown}/>
            <button className="send-button" onClick={sendMessage}><i className="bi bi-send-fill"></i></button>
        </>
    );
}

export default MessageBox