# https://gitlab.com/gitlab-com/support/test-projects/ci-examples/secure/iac-scanning/sample-tf-scan/-/tree/main
resource "aws_security_group" "kics_test_group" {
  name        = "kics-test-group"
  description = "intentional full access SG to trip kics in ci"
  vpc_id      = data.aws_vpc.app.id

  # this resource should fail for having an unrestricted ingress rule
  # https://docs.kics.io/latest/queries/terraform-queries/aws/4728cd65-a20c-49da-8b31-9c08b423e4db/
  ingress {
    cidr_blocks = ["0.0.0.0/0"]
    description = "intentional full access SG to trip kics in ci"
    from_port   = 0
    protocol    = "-1"
    to_port     = 0
  }
}

resource "aws_security_group_rule" "kics_test_egress" {
  # this resource should fail for a missing "description" parameter
  # https://docs.kics.io/latest/queries/terraform-queries/aws/68eb4bf3-f9bf-463d-b5cf-e029bb446d2e/

  cidr_blocks = ["0.0.0.0/0", ]
  from_port         = 0
  protocol          = "-1"
  security_group_id = aws_security_group.kics_test_group.id
  to_port           = 0
  type              = "egress"
}