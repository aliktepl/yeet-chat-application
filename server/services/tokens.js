const User = require('../models/user');
const secretKey = 'some super secret key shhhhhhhhhhhhhh!!!!!!!!!!';
const jwt = require('jsonwebtoken');
const generateToken = async (username, password) => {

    // Check if the username and password exist in the User database
    const user = await User.findOne({ username, password });

    if (user) {
        const payload = { username, password };
        return await jwt.sign(payload, secretKey);
    } else {
        return null;
    }
};

module.exports = { generateToken };
