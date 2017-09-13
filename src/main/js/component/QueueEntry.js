'use strict';

import React from 'react';

export default class QueueEntry extends React.Component{
    render() {
        return (
            <tr>
                <td>
                    <span className="glyphicon glyphicon-plane" aria-hidden="true"></span> {this.props.entry.airCraft.type}</td>
                <td>{this.props.entry.airCraft.size}</td>
                <td>{this.props.entry.requestTime}</td>
            </tr>
        )
    }
}
