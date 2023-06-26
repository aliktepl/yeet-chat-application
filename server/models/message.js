const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const User = require('./user');
const Message = new Schema({
    id: {
        type: Number,
        required: true
    },
    created: {
        type: String,
        required: Date
    },
    sender: {
        type: Schema.Types.ObjectId,
        ref: 'User', // Referencing the User model
        required: true
    },
    content: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('Message', Message);