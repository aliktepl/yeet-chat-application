const chatsController = require('../controllers/chats');
const messagesController = require('../controllers/message')
const { isLoggedIn } = require('../controllers/tokens');

const express = require('express');
const router = express.Router();

// get/create chats
router.route('/')
    .get(isLoggedIn, chatsController.getChats)
    .post(isLoggedIn, chatsController.createChats);

// get/create messages
router.route('/:id/Messages')
    .post(isLoggedIn, messagesController.createMessage)
    .get(isLoggedIn, messagesController.getMessage)

// get/delete chat
router.route('/:id')
    .delete(isLoggedIn,chatsController.deleteChat)
    .get(isLoggedIn, chatsController.getChatID);


module.exports = router;
