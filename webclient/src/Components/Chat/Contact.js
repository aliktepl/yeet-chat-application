function Contact({contacts, handleContactIndex, activeContactIndex, messages}) {

    function truncateMessage(message) {
        if (message.length > 30) {
            return message.slice(0, 27) + '...';
        }
        return message;
    }

    function ContactItem({contact, contactIndex}){
        const activeChat = 'list-group-item list-group-item-action active';
        const passiveChat = 'list-group-item list-group-item-action';

        function handleClick(){
            handleContactIndex(contactIndex);
        }

        function RenderLastMessageTime() {
            if(contact.lastMessage) {
                const dateObj = new Date(contact.lastMessage.created);
                return(<p className="msg_time">{dateObj.getHours().toString().padStart(2, '0')}:{dateObj.getMinutes().toString().padStart(2, '0')}</p>)
            }
        }


        return (
            <div className="contact">
                <a onClick={handleClick} className={activeContactIndex === contactIndex ?
                    activeChat : passiveChat} aria-current="true">
                    <img src={contact.user.profilePic} className="rounded-circle prof_img" alt="prof_img"></img>
                    <h1 className="prof_name">{contact.user.displayName}
                        {contact.lastMessage && <div className="last-msg">{truncateMessage(contact.lastMessage.content)}</div>}
                        {/*{RenderLastMessage()}*/}
                    </h1>
                    {RenderLastMessageTime()}
                </a>
            </div>
        );
    }
    return (
        <div>
            {contacts && contacts.map((contact, index) => (
                <ContactItem key={contact.id} contact={contact} contactIndex={index}/>
            ))}
        </div>
    );
}

export default Contact;