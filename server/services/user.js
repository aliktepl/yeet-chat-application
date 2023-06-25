const User = require('../models/user');

const createUser = async (username, password, displayName, profilePic) => {
    const user = await new User({username: username, password: password, displayName: displayName, profilePic: profilePic});
    return await user.save();
};

const getUser = async (passedName, username) => {
    try {
        // Perform further operations based on the user ID
        if (passedName === username) {
            return User.findOne({'username': username});
        }
    } catch (error) {
        // Handle token verification failure
    }
};

module.exports = {createUser, getUser};