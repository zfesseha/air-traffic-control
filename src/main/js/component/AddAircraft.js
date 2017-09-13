'use strict';

import {Button, Form} from 'react-bootstrap';
import InputGroup from './InputGroup';

import React from 'react';

export default class AddAircraft extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        Object.keys(this.props.attributes).forEach(attribute => {
            this.state[attribute] = this.props.attributes[attribute];
        });
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        var queueRequest = {type : "ENQUEUE", airCraft : {}};
        Object.keys(this.props.attributes).forEach(attribute => {
            queueRequest.airCraft[attribute] = this.state[attribute];
        });
        this.props.onCreate(queueRequest);
    }

    handleChange(value,  event) {
        var changeObj = {};
        changeObj[value] = event.target.value;
        this.setState(changeObj);
    }

    render() {
        var inputs = Object.keys(this.props.attributes).map(attribute =>
                <InputGroup
                    key={attribute}
                    type="text"
                    label={attribute}
                    attribute={attribute}
                    value={this.props.attributes[attribute]}
                    onChange={this.handleChange.bind(this, attribute)}
                    placeholder={attribute}
                />
        );
        return (
            <div>
                <Form inline>
                    {inputs}
                    <Button className="ac-button" bsStyle="warning" type="submit" onClick={this.handleSubmit}>Add plane to Queue</Button>
                </Form>
            </div>
        )
    }
}
