
const userService = require('../services/tokens');
const secretKey = 'some super secret key shhhhhhhhhhhhhh!!!!!!!!!!';
const jwt = require('jsonwebtoken');

const generateToken = async (req, res) => {
    const response = await userService.generateToken(req.body.username, req.body.password);
    if(response) {
        res.status(200).send(response);
    } else {
        res.status(404).send('Invalid username or password');

    }
};

// Ensure that the user sent a valid token
const isLoggedIn = (req, res, next) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
    // Extract the token from that header
        const token = req.headers.authorization.split(" ")[1];
        try {
    // Verify the token is valid
            const data = jwt.verify(token, secretKey);
            req.headers = { "passedName" : data.username }
    // Token validation was successful. Continue to the actual function (index)
            return next()
        } catch (err) {
            return res.status(404).send('Authentication error');
        }
    }
    else
        return res.status(404).send('Incorrect username and/or password');
}

module.exports = { generateToken, isLoggedIn };