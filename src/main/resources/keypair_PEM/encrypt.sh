echo '{"name": "prayagupd"}' > input_payload.json
openssl rsautl -encrypt -inkey public_key.pem -pubin -in input_payload.json -out output_payload.enc

file output_payload.enc
