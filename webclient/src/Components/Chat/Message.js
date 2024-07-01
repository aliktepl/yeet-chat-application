function Message({messages, contact}) {
    return (
        <div>
            {messages.slice().reverse().map((message) => (
                <MessageItem key={message.id} message={message} contact={contact}/>
            ))}
        </div>
    );
}


function MessageItem({message, contact}) {
    const dateObj = new Date(message.created)
    return (
        <div className={message.sender.username === contact.user.username ? "left-text" : "right-text"}>
            {message.content}
            <div className="timestamp">
                {dateObj.getHours().toString().padStart(2, '0')}:{dateObj.getMinutes().toString().padStart(2, '0')}
            </div>
        </div>
    );
}

export default Message