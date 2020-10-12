var ReactDispatcher = require('../../node_modules/flux/lib/Dispatcher');
const DispatcherHelper = new ReactDispatcher();

function register(fun){
    DispatcherHelper.register(fun())
}

function dispatch(actionType){
    DispatcherHelper.dispatch({
        actionType: actionType
    })
}

export default DispatcherHelper;
