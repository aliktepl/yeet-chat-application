const Chats = require('../models/chats');
const User = require('../models/user');
const Messages = require('../models/message')

async function getId() {
    let index = 0
    const chats = await Chats.find({})
    chats.forEach(() => {
        index++
    })
    return index
}


const deleteChat = async (chat) => {
    for (const message of chat.messages) {
        await Messages.deleteOne({_id : message})
    }
    await Chats.deleteOne({id : chat.id})
}

const createChats = async (passedName, username) => {
    const id = await getId();
    const user1 = await User.findOne({username: passedName});
    const user2 = await User.findOne({username});
    const chats = await new Chats({
        id: id,
        users: [{
            username: user1.username,
            displayName: user1.displayName,
            profilePic: user1.profilePic
        }, {username: user2.username,
            displayName: user2.displayName,
            profilePic: user2.profilePic
        }],
        messages: []
    });
    const chat = {
        id: id,
        user: {
            username: user2.username,
            displayName: user2.displayName,
            profilePic: user2.profilePic
        },
    }
    await chats.save();
    return chat;
};

const getChats = async (passedName) => {
    const userMyself = await User.findOne({ username: passedName });
    const chats = await Chats.find(); // Retrieve all chats
    const myChats = [];

    for (const chat of chats) {
        const hasUser = chat.users.some((user) => user.username === userMyself.username);
        if (hasUser) {
            const user1 = await User.findOne({username: chat.users[0].username});
            let lastMsg = await chat.messages[chat.messages.length - 1]
            let response;
            if(lastMsg){
                lastMsg = await Messages.findById(lastMsg)
                response = {id: chat.id, user: [], "lastMessage": lastMsg}
            } else {
                response = {id: chat.id, user: [], "lastMessage": null}
            }
            if(user1.username !== passedName){
                //push parsedChat.users[0]
                response.user = chat.users[0]
            } else {
                // push parsedChat.users[1]
                response.user = chat.users[1]
            }
            myChats.push(response);
        }
    }
    return myChats;
};
// id , sender->user, lst_msg

const getChatID = async (chat) => {
    return chat // return the chat
}

module.exports = {createChats, getChats, getChatID, deleteChat};
