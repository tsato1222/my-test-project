import boto3

aws_access_key = 'AKIA****WwMA****CDEF'
aws_secret_key = '0e8/****twHF****ZNvq****tn7U****PPeq****'

session = boto3.Session(
    aws_access_key_id=aws_access_key,
    aws_secret_access_key=aws_secret_key,
    region_name='us-west-2'
)

s3 = session.client('s3')

response = s3.list_buckets()

for bucket in response['Buckets']:
    print(f'- {bucket["Name"]}')
