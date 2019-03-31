import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initalState = {};
const middleware = [thunk];

let store;

const RRDT = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

if (window.navigator.userAgent.includes("Chrome") && RRDT) {
  store = createStore(
    rootReducer,
    initalState,
    compose(
      applyMiddleware(...middleware),
      RRDT
    )
  );
} else {
  store = createStore(rootReducer, initalState, compose(applyMiddleware(...middleware)));
}

export default store;
