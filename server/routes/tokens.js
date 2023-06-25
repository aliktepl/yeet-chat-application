const userToken = require('../controllers/tokens');
const express = require('express');
var router = express.Router();
router.route('/')
    // .get(userController.getUsers)
    .post(userToken.generateToken);

module.exports = router;