const userController = require('../controllers/user');
const tokenController = require('../controllers/tokens')
const express = require('express');
var router = express.Router();

router.route('/').post(userController.createUser);
router.route('/:username').get(tokenController.isLoggedIn, userController.getUser);

module.exports = router;
