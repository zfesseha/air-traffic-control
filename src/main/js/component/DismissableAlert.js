'use strict';

import {Alert} from 'react-bootstrap';

import React from 'react';
export default class DismissableAlert extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            alertVisible: this.props.alertVisible
        };
        this.handleAlertDismiss = this.handleAlertDismiss.bind(this);
        this.handleAlertShow = this.handleAlertShow.bind(this);
    }

    render() {
        if (this.state.alertVisible) {
            return (
                <Alert bsStyle={this.props.style} onDismiss={this.handleAlertDismiss}>
                    <h4>{this.props.title}</h4>
                    <div>{this.props.description}</div>
                </Alert>
            );
        }
        return null;
    }

    handleAlertDismiss() {
        this.setState({alertVisible: false});
    }

    handleAlertShow() {
        this.setState({alertVisible: true});
    }
}