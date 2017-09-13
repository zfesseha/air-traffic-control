'use strict';

import {
    FormGroup,
    FormControl
} from 'react-bootstrap';

import React from 'react';

export default class InputGroup extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            inputOptions : {
                type : ["PASSENGER", "CARGO"],
                size : ["LARGE", "SMALL"]
            }
        }
    }
    render() {
        var options = this.state.inputOptions[this.props.attribute].map(anOption => {
            return <option key={anOption} value={anOption}>{anOption}</option>;
        })
        return (
            <FormGroup className="ac-input-group" controlId={this.props.id}>
                <FormControl className="ac-control"
                             onChange={this.props.onChange}
                             componentClass="select"
                             // ref={this.props.ref}
                             placeholder="Please Select"
                             // placeholder={this.props.placeholder}
                >
                    {options}
                </FormControl>
            </FormGroup>
        )
    }
}
