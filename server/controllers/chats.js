const chatsService = require('../services/chats')
const User = require('../models/user')
const Chats = require('../models/chats')

const deleteChat = async (req, res) => {
    const chatExist = await Chats.findOne({id : req.params.id})
    if(chatExist){
        res.json(await chatsService.deleteChat(chatExist))
    } else {
        res.status(404).send()
    }
}

const createChats = async (req, res) => {
    const userExist = await User.findOne({username: req.body.username})
    // check if the user in for chat creation request exists
    if(userExist){
        res.status(200).json(await chatsService.createChats(req.headers.passedName, req.body.username))
    } else{
        res.status(400).send('No such user')
    }
};

const getChats = async (req, res) => {
    const chats = await chatsService.getChats(req.headers.passedName)
    res.json(chats)
}

const getChatID = async (req, res) => {
    const chat = await Chats.findOne({id : req.params.id})
    if(chat){
        res.json(await chatsService.getChatID(chat))
    } else {
        res.status(401).send()
    }
}

module.exports = {createChats, getChats, deleteChat, getChatID}
