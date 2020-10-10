import React from 'react';
import './App.css';
import AppWrapper from "carbon-react/lib/components/app-wrapper";
import { Table, TableRow, TableCell, TableHeader } from "carbon-react/lib/components/table";

function App() {

  return (
    <AppWrapper>
      <Table>
        <TableRow>Row row</TableRow>
      </Table>
    </AppWrapper>
  );

}

export default App;
