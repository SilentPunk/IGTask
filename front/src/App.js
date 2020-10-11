import React from "react";
import "./App.css";
import AppWrapper from "carbon-react/lib/components/app-wrapper";
import ActionTable from "./action-table/action-table";
import Heading from "carbon-react/lib/components/heading";
import NavigationBar from "carbon-react/lib/components/navigation-bar";

function registerUser(userName) {
  return fetch("http://localhost:8080/user", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ identifier: userName }),
  }).then(result => result.json());
}

function App() {
  let userName = "TestowyUżytkownik";
  let userData = registerUser(userName);

  return (
    <AppWrapper>
      <Heading
        backLink=""
        divider
        separator={false}
        subheader="This is just simple app for task purpose - enjoy!"
        title="My favorite stock prices"
      ></Heading>
      <ActionTable
        fetchFunction={() => fetch("http://localhost:8080/stocks")}
        itemsToFetch={["stockName", "currentPrice"]}
        actionButtonName="Add to Bookmarks"
        columns={["Stock Name", "Current Price"]}
        actionButtonOnClick={(rowData) => {
          fetch("http://localhost:8080/bookmark", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              user: userName,
              stock: rowData.stockName,
              stockPrice: rowData.currentPrice,
            }),
          });
        }}
      ></ActionTable>
      <ActionTable
        fetchFunction={() =>
          fetch("http://localhost:8080/user/" + 1 + "/bookmarks", {
            headers: {
              "Content-Type": "application/json",
            }
          })
        }
        itemsToFetch={["stockName", "creationTimestamp", "stockPrice"]}
        actionButtonName="Remove"
        columns={["Stock Name", "Timestamp", "Price"]}
        actionButtonOnClick={(rowData) => {}}
      ></ActionTable>
    </AppWrapper>
  );
}

export default App;
