import React, { Component } from "react";
import { login } from "../../actions/securityActions";
import classnames from "classnames";
import { connect } from "react-redux";

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: "",
      password: "",
      errors: {}
    };
  }

  componentDidMount = () => {
    if (this.props.user.validToken) {
      this.props.history.push("/dashboard");
    }
  };

  componentWillReceiveProps = nextProps => {
    if (nextProps.errors) {
      this.setState({ ...this.state, errors: nextProps.errors });
    }

    if (nextProps.user.validToken) {
      this.props.history.push("/dashboard");
    }
  };

  onChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };

  onSubmit = e => {
    e.preventDefault();
    const user = {
      username: this.state.username,
      password: this.state.password
    };
    this.props.login(user);
  };
  render() {
    const { errors } = this.state;

    return (
      <div className="login">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h1 className="display-4 text-center">Log In</h1>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="email"
                    className="form-control form-control-lg"
                    placeholder="Email Address"
                    name="username"
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.username
                    })}
                    value={this.state.username}
                  />
                  {errors.username && <div className="invalid-feedback">{errors.username}</div>}
                </div>
                <div className="form-group">
                  <input
                    type="password"
                    className="form-control form-control-lg"
                    placeholder="Password"
                    name="password"
                    onChange={this.onChange}
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.password
                    })}
                    value={this.state.password}
                  />
                  {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                </div>
                <input type="submit" className="btn btn-info btn-block mt-4" />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
const mapStateToProps = state => ({
  errors: state.errors,
  user: state.user
});

export default connect(
  mapStateToProps,
  { login }
)(Login);
