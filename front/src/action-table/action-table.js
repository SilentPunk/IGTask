import React from "react";
import {
  Table,
  TableRow,
  TableCell,
  TableHeader,
} from "carbon-react/lib/components/table";
import Button from "carbon-react/lib/components/button";

class ActionTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
      columns: this.props.columns || [],
      actionButtonName: this.props.actionButtonName,
      actionButtonOnClick: this.props.actionButtonOnClick,
      fetchFunction: this.props.fetchFunction.bind(this),
      itemsToFetch: this.props.itemsToFetch || [],
    };
  }

  makeCall() {
    this.state
      .fetchFunction()
      .then((result) => result.json())
      .then(
        (result) => {
          this.setState({
            items: Array.from(result),
          });
        },
        (error) => {
          this.setState({
            items: []
          });
        }
      );
  }

  componentDidMount() {
    this.makeCall();
    setInterval(() => this.makeCall(), 5000);
  }

  createTableCells(rowData) {
    const cellData = [];
    this.state.itemsToFetch.forEach((item) =>
      cellData.push(<TableCell>{rowData[item]}</TableCell>)
    );

    cellData.push(
      <TableCell>
        {
          <Button
            buttonType="primary"
            size="small"
            onClick={this.state.actionButtonOnClick.bind(this, rowData)}
          >
            {this.state.actionButtonName}
          </Button>
        }
      </TableCell>
    );

    return cellData;
  }

  createTableHeaders() {
    let headersTable = [];
    this.state.columns.forEach((column) => {
      headersTable.push(<TableHeader>{column}</TableHeader>);
    });

    return headersTable;
  }

  render() {
    let tableRows = this.state.items.map((rowData, key) => {
      return <TableRow>{this.createTableCells(rowData)}</TableRow>;
    });

    let headersTable = this.createTableHeaders();
    tableRows.unshift(
      <TableRow>
        {headersTable}
        <TableHeader>Action</TableHeader>
      </TableRow>
    );

    return <Table>{tableRows}</Table>;
  }
}

export default ActionTable;
