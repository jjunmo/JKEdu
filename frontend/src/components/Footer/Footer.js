import styled from "styled-components";
import { useNavigate } from "react-router-dom";

import "App.css";

const FooterDiv = styled.footer`
  text-align: center;
  background-color: #f0f5f9;
  font-size: 16px;
  height: 50px;
  line-height: 50px;
  color: white;
  display: flex;
  justify-content: space-between;

  @media screen and (max-width: 768px) {
    font-size: 12px;
    height: 30px;
    line-height: 30px;
  }
`;

const PrivacyList = styled.div`
  margin-left: 15px;
  display: flex;
  background-color: #f0f5f9;
  color: #52616b;

  > div {
    cursor: pointer;
    :nth-child(1) {
      ::after {
        content: "|";
        padding: 0px 10px;
      }
    }
    :nth-child(2) {
      ::after {
        content: "|";
        padding: 0px 10px;
      }
    }
  }

  @media screen and (max-width: 768px) {
    > div {
      cursor: pointer;
      :nth-child(1) {
        ::after {
          content: "|";
          padding: 0px 5px;
        }
      }
      :nth-child(2) {
        ::after {
          content: "|";
          padding: 0px 5px;
        }
      }
    }
  }
`;

const CopyRight = styled.div`
  font-family: SCDream5;
  color: black;

  > div {
    margin-right: 15px;
  }
`;

const Footer = () => {
  const navigate = useNavigate();

  return (
    <FooterDiv>
      <PrivacyList>
        <div onClick={() => navigate("/term-of-service")}>이용 약관</div>
        <div onClick={() => navigate("/privacy-policy")}>
          개인정보 처리 방침
        </div>
        <div onClick={() => navigate("/service-center")}>고객 센터</div>
      </PrivacyList>
      <CopyRight>
        <div>Copyright (주) 제이케이에듀</div>
      </CopyRight>
    </FooterDiv>
  );
};

export default Footer;
