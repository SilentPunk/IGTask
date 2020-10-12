import React from "react";
import "./App.css";
import AppWrapper from "carbon-react/lib/components/app-wrapper";
import ActionTable from "./action-table/action-table";
import Heading from "carbon-react/lib/components/heading";
import Pod from "carbon-react/lib/components/pod";
import DispatcherHelper from "./dispatcher-helper/dispatcher-helper"

function App() {
  let userName = "test";

  return (
    <AppWrapper>
      <Heading
        backLink=""
        divider
        separator={false}
        subheader=""
        title="Welcom to IG Live Stock Prices Simple App"
      ></Heading>
      <Pod className="stockPricesPod">
        <Heading
          backLink=""
          divider
          separator={false}
          subheader="Just click to add your favorite"
          title="Live stock prices"
        ></Heading>
        <ActionTable
          fetchFunction={() => fetch("http://localhost:8080/stocks")}
          itemsToFetch={["stockName", "currentPrice"]}
          actionButtonName="Add to Bookmarks"
          columns={["Stock Name", "Current Price"]}
          actionButtonOnClick={(obj, rowData) => {
            fetch("http://localhost:8080/user/1/bookmark", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify({
                stock: {
                  stockName: rowData.stockName,
                },
                currentPrice: rowData.currentPrice,
              }),
            });
            DispatcherHelper.dispatch("refreshUserBookmarks")
          }}
        ></ActionTable>
      </Pod>
      <Pod className="bookmarksPod">
        <Heading
          backLink=""
          divider
          separator={false}
          subheader=""
          title="Your bookmarks"
        ></Heading>
        <ActionTable
          refreshDispatcherEvent="refreshUserBookmarks"
          fetchFunction={() =>
            fetch("http://localhost:8080/user/" + 1 + "/bookmarks", {
              headers: {
                "Content-Type": "application/json",
              },
            })
          }
          itemsToFetch={["stockName", "creationTimestamp", "stockPrice"]}
          actionButtonName="Remove"
          columns={["Stock Name", "Timestamp", "Price"]}
          actionButtonOnClick={(obj, rowData) => {
            fetch("http://localhost:8080/user/1/bookmark/" + rowData.id, {
              method: "DELETE",
            });
            DispatcherHelper.dispatch("refreshUserBookmarks")
          }}
        ></ActionTable>
      </Pod>
    </AppWrapper>
  );
}

export default App;
