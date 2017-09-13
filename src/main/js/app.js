'use strict';

import Welcome from './component/Welcome';
import AddAircraft from './component/AddAircraft';
import AircraftQueue from './component/AircraftQueue';
import EntryAddedAlert from './component/EntryAddedAlert';
import DequeuedAlert from './component/DequeuedAlert';
import CustomButton from './component/CusomButton';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {queue: [],
            attributes: {"type" : 'PASSENGER', "size" : 'LARGE'},
            isQueueStarted : false
        };
        // this.updatePageSize = this.updatePageSize.bind(this);
        this.startQueue = this.startQueue.bind(this);
        this.retrieveQueue = this.retrieveQueue.bind(this);
        this.onCreate = this.onCreate.bind(this);
        this.dequeue = this.dequeue.bind(this);
        this.stopQueue = this.stopQueue.bind(this);
    }

	componentDidMount() {
		this.retrieveQueue();
	}

    startQueue() {
        client({method: 'GET', path: '/api/v1/queue/start'}).done(response => {
            // console.log("response:\t" + JSON.stringify(response, null, "\t"))
            this.setState({
                isQueueStarted: true
            });
            this.retrieveQueue();
        });
    }

    retrieveQueue() {
        client({method: 'GET', path: '/api/v1/queue'}).done(response => {
            this.setState({
                queue: response.entity
            });
        });
    }

    dequeue() {
        this.setState({
            dequeuedEntry: null,
            insertResult: null
        });
        client({method: 'GET', path: '/api/v1/queue/dequeue'}).done(response => {
            this.setState({
                dequeuedEntry: response.entity
            });
            this.retrieveQueue();
        });
    }

    stopQueue() {
        this.setState({
            dequeuedEntry: null,
            insertResult: null
        });
        this.setState({
            isQueueStarted: false
        });
    }

    onCreate(newEntry) {
        this.setState({
            dequeuedEntry: null,
            insertResult: null
        });
        client({
            method: 'POST',
            path: '/api/v1/queue',
            entity: newEntry,
            headers: {'Content-Type': 'application/json'}
        }).done(response => {
            this.setState({
                insertResult: response.entity
            });
        	this.retrieveQueue();
        });
    }

	render() {
        // console.log("state:\t" + JSON.stringify(this.state, null, "\t"))
        if (this.state.isQueueStarted) {
            return (
                <div>
                    <EntryAddedAlert insertResult={this.state.insertResult}/>
                    <DequeuedAlert dequeuedEntry={this.state.dequeuedEntry}/>
                    <div>
                        <CustomButton onSubmit={this.dequeue} title="Next Plane" style="danger"/>
                        <CustomButton onSubmit={this.stopQueue} title="Exit" style="primary"/>
                    </div>
                    <br/>
                    <div>
                        <AddAircraft attributes={this.state.attributes} onCreate={this.onCreate}/>
                    </div>
                    <br/>
                    <AircraftQueue queue={this.state.queue}/>
                </div>
            )
        } else {
            return (
                <div>
                    <Welcome onStart={this.startQueue}/>
                </div>
            )
        }
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
