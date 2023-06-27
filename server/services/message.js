const Messages = require('../models/message');
const User = require('../models/user')

async function messageCounter() {
    let index = 0
    const messages = await Messages.find({})
    messages.forEach(() => {
        index++
    })
    return index
}

const createMessage = async (chat, message, username) => {
    const sender = await User.findOne({username: username})
    const newMsg = await new Messages({
        id : await messageCounter(),
        created : new Date(),
        sender: sender.id,
        content : message})
    // save new msg
    newMsg.save();
    // save pushed message to chat
    chat.messages.push(newMsg)
    chat.save();
    return newMsg
}

const getMessage = async (chat) => {
    // extract pointer references and return msg by value
    let msgList = []
    for (const msg of chat.messages) {
        const msgContent = await Messages.findById(msg);
       const user = await User.findById(msgContent.sender);
       const newMsg = {
           id : msgContent.id,
           created : msgContent.created,
           sender : user,
           content : msgContent.content
       }
       msgList.push(newMsg);
    }
    return msgList.slice().reverse();
}



module.exports = {createMessage, getMessage};
