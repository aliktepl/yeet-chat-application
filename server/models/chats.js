const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const User = require('./user');
const Message = require('./message');
const Chats = new Schema({
    id: {
        type: Number,
        required: true
    },
    users: [{
        username: {
            type: String,
            required: true
        },
        displayName: {
            type: String,
            required: true
        },
        profilePic: {
            type: String,
            required: true
        }
    }],
    messages: [{
        type: Schema.Types.ObjectId,
        ref: 'Message', // Referencing the User model
        required: true
    }]
});

module.exports = mongoose.model('Chats', Chats);