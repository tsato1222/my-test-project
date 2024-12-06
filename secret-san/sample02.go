package main

import (
	"database/sql"
	"fmt"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

const dsn = "mysql://root:Q4vF1ntgh5gt$@MyInternalService/SignIn/action"

func main() {
	db, err := sql.Open("mysql", dsn)
	if err != nil {
		log.Fatalf("Failed to connect to the database: %v", err)
	}
	defer db.Close()

	fmt.Println("Successfully connected to the database!")
}
