import boto3

aws_access_key_dummy = 'AKIAIOSFODNN7EXAMPLE'
aws_access_key = 'AKIAIOSFODNN7EXAMPLE'
aws_secret_key = 'wJalrXUtnFEMI/K7MDENG/bPxRfiCYzEXAMPLEKEY'

session = boto3.Session(
    aws_access_key_id=aws_access_key,
    aws_secret_access_key=aws_secret_key,
    region_name='us-west-2'
)

s3 = session.client('s3')

response = s3.list_buckets()

for bucket in response['Buckets']:
    print(f'- {bucket["Name"]}')
