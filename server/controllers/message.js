const messageService = require('../services/message')
const Chats = require('../models/chats')
const Chat = require("../models/chats")
//firebase
const firebase = require("./firebase")

// req.body.id -> chat id
// req.header.authorization -> token
const createMessage = async (req, res) => {
    if (typeof req.params === 'string') {
        res.status(500).send()
    }
    try{
        const doesExist = await Chats.findOne({id : req.params.id})
        if(doesExist){
            res.json(await messageService.createMessage(doesExist, req.body.msg, req.headers.passedName))
            //firebase
            if(req.headers.passedName === doesExist.users[0].username){
                if (firebase.users.has(doesExist.users[1].username)) {
                    firebase.sendMessage(firebase.users.get(doesExist.users[1].username), "Message Received", req.body.msg, req.params.id)
                }
            } else {
                if (firebase.users.has(doesExist.users[0].username)) {
                    firebase.sendMessage(firebase.users.get(doesExist.users[0].username), "Message Received", req.body.msg, req.params.id)
                }
            }
        } else {
            res.status(401).send()
        }
    } catch (err){
        res.status(401).send()
    }
}

const getMessage = async (req, res) => {
    const doesExist = await Chat.findOne({id: req.params.id})
    if(doesExist){
        await res.json(await messageService.getMessage(doesExist))
    } else {
        res.status(401).send()
    }
}



module.exports = {createMessage, getMessage};
